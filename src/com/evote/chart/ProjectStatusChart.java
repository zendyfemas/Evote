/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evote.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.evote.adapter.CalonAdapter;
import com.evote.main.R;
import com.evote.main.VoteActivity;
import com.evote.main.VoteActivity.GetData;
import com.evote.model.Statistik;
import com.evote.parser.ParsedCalonDataSet;
import com.evote.parser.ParsedStatistikDataSet;

import android.R.bool;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;

/**
 * Project status demo chart.
 */
public class ProjectStatusChart extends AbstractDemoChart {
	int[] colors = new int[] { Color.BLUE, Color.GREEN,Color.MAGENTA,Color.RED,Color.BLACK };
	/**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Project tickets status";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The opened tickets and the fixed tickets (time chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
	 Context[] params=new Context[1];
	 params[0]=context;
     return new GetData().doInBackground(params);
  }
  public class GetData extends AsyncTask<Context, Void, Intent>{
	  ProgressDialog dialog;
	  String hasil="";
		
		
		@Override
		protected Intent doInBackground(Context... params) {
			ArrayList<Statistik> listitem=null;
			try {
				listitem=new ParsedStatistikDataSet().parse();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			ArrayList<String> tempTitle=new ArrayList<String>();
			for(Statistik s:listitem){
				boolean ada=false;
				for(String t:tempTitle){
					if(t.equals(s.getNama())){
						ada=true;
						break;
					}
				}
				if(!ada){
					tempTitle.add(s.getNama());
				}
			}
			
			ArrayList<String> tempDate=new ArrayList<String>();
			for(Statistik s:listitem){
				boolean ada=false;
				for(String t:tempDate){
					if(t.equals(s.getTanggal())){
						ada=true;
						break;
					}
				}
				if(!ada){
					tempDate.add(s.getTanggal());
				}
			}
			
			String[] titles = new String[tempTitle.size()];
			for(int j=0;j<tempTitle.size();j++){
				titles[j]=tempTitle.get(j);
			}
			
		    List<Date[]> dates = new ArrayList<Date[]>();
		    List<double[]> values = new ArrayList<double[]>();
		    int length = titles.length;
		    int idx=0;
		    for (int i = 0; i < length; i++) {
		      dates.add(new Date[tempDate.size()]);
		      double[] jum=new double[tempDate.size()];
		      idx=0;
		      int idxjum=0;
		      for(String date:tempDate){
		    	  dates.get(i)[idx] = new Date(Integer.parseInt(date.split("-")[0])-1900, Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
		    	  boolean adavote = false;
		    	  for(Statistik st:listitem){
		    		  adavote=false;
		    		  if((st.getNama().equals(titles[i]))&&(st.getTanggal().equals(date))){
		    			  jum[idxjum]=Double.parseDouble(st.getJumlah());
		    			  adavote=true;
		    			  
		    		  }
		    		  if(adavote){
		    			  break;
		    		  }
		    	  }
		    	  idxjum++;
		    	  idx++;
		      }
		      values.add(jum);
		      
		    }
		    
		    length = values.get(0).length;
		    int[] colorsp = new int[titles.length];
		    for(int i=0;i<titles.length;i++){
		    	colorsp[i]=colors[i];
		    }
		    PointStyle[] styles = new PointStyle[titles.length];
		    for(int i=0;i<titles.length;i++){
		    	styles[i]=PointStyle.POINT;
		    }
		    XYMultipleSeriesRenderer renderer = buildRenderer(colorsp, styles);
		    setChartSettings(renderer, "Statistik Pemilihan Suara", "Date", "Jumlah", dates.get(0)[0].getTime(),
		        dates.get(0)[tempDate.size()-1].getTime(), 1, 10,Color.GRAY, Color.LTGRAY);
		    renderer.setXLabels(5);
		    renderer.setYLabels(10);
		    length = renderer.getSeriesRendererCount();
		    for (int i = 0; i < length; i++) {
		      SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
		      seriesRenderer.setDisplayChartValues(true);
		    }
		    //return new GetData().doInBackground();
		    return ChartFactory.getTimeChartIntent(params[0], buildDateDataset(titles, dates, values),
		        renderer, "dd/MM/yyyy");
		}

		

		
	  
  }
}
