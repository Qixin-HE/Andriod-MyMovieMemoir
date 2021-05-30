package com.example.a2.fragment;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a2.R;
import com.example.a2.networkconnection.NetworkConnection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public class ReportFragment extends Fragment {
    private View vReport;
    private Date startDate;
    private Date endDate;
    private DatePickerDialog picker;
    private BarChart barChart;
    private Button start;
    private Button end;
    private TextView startdate;
    private TextView enddate;
    private Button pieSubmit;
    private Button chart_submit;
    private String datePicked = "";
    private String dateShow = "";
    private PieChart piechart;
    private Spinner spinner;
    private String yearPicked;
    private List<Integer> yValue = new ArrayList<>();
    private List<String> xValue = new ArrayList<>();
    private List<IBarDataSet> dataSets = new ArrayList<>();
    private LinkedHashMap<String, List<Integer>> chartDataMap = new LinkedHashMap<>();
    private BarChart barGraph;
    private NetworkConnection networkConnection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vReport = inflater.inflate(R.layout.report_fragment, container, false);
        start = vReport.findViewById(R.id.btStart);
        end = vReport.findViewById(R.id.btEnd);
        startdate = vReport.findViewById(R.id.tvStartdate);
        enddate = vReport.findViewById(R.id.tvEnddate);
        //piechart = vReport.findViewById(R.id.chartPie);
        spinner = vReport.findViewById(R.id.spChart);
        barGraph = vReport.findViewById(R.id.chartPie);

        networkConnection = new NetworkConnection();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(startdate);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(enddate);
            }
        });
        pieSubmit = vReport.findViewById(R.id.btSubmitpie);
        pieSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getMovieCount().execute(startdate.getText().toString(),enddate.getText().toString());
            }
        });
        List<String> year = new ArrayList<>();
        year.add("2020");year.add("2019");year.add("2018");year.add("2017");year.add("2016");year.add("2015");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ReportFragment.this.getActivity(), android.R.layout.simple_spinner_item, year);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = parent.getItemAtPosition(position).toString();
                if (selectedState != null) {
                    yearPicked = selectedState;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yearPicked = "2020";
            }
        });
        chart_submit = vReport.findViewById(R.id.btSubmitchart);
        chart_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new showBarChart().execute(yearPicked);
            }
        });

        return vReport;
    }

    private class getMovieCount extends AsyncTask<String, Void, List<PieEntry>> {
        @Override
        protected List<PieEntry> doInBackground(String... time) {

            return networkConnection.getMovieCount(time[0]);
        }
        @Override
        protected void onPostExecute(List<PieEntry> data) {
            piechart.setUsePercentValues(false);
            piechart.getDescription().setEnabled(false);
            piechart.setExtraOffsets(5, 10, 5, 5);
//            pieChart.setHoleRadius(28f);
            ArrayList<Integer> colors = new ArrayList<Integer>();
            for (int c : ColorTemplate.MATERIAL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());
            PieDataSet pieDataSet = new PieDataSet(data, "");
            pieDataSet.setColors(colors);
            PieData pie = new PieData();
            pie.setDataSet(pieDataSet);
            piechart.setData(pie);
            piechart.invalidate();

        }
    }


    private class showBarChart extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... year) {

            return networkConnection.MovieCountMonth(year[0]);
        }
        @Override
        protected void onPostExecute(String data) {
            BarData data2 = new BarData(dataSets);
            data2.setBarWidth(0.5f);
            int barAmount = chartDataMap.size();
            float groupSpace = 0.3f;
            float barWidth = (1f - groupSpace) / barAmount;
            float barSpace = 0f;
            data2.setBarWidth(barWidth);
            //data2.groupBars(0f, groupSpace, barSpace);
            barGraph.setBackgroundColor(Color.WHITE);
            barGraph.setDrawGridBackground(false);
            barGraph.setDrawBarShadow(false);
            barGraph.setHighlightFullBarEnabled(false);
            barGraph.setDrawBorders(true);
            barGraph.setData(data2);
            XAxis xAxis = barGraph.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(xValue.size());
            xAxis.setCenterAxisLabels(true);
            YAxis leftAxis = barGraph.getAxisLeft();
            YAxis rightAxis = barGraph.getAxisRight();
            leftAxis.setAxisMinimum(0f);
            rightAxis.setAxisMinimum(0f);

            Legend legend = barGraph.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(11f);

            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

            legend.setDrawInside(false);
            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return xValue.get((int) Math.abs(value) % xValue.size());
                }
            });
        }

    }

    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        barDataSet.setDrawValues(false);

    }


    public void showDatePicker(final TextView tv){

        Calendar calendar = Calendar.getInstance();
        int myear = calendar.get(Calendar.YEAR);
        int mmonthOfYear = calendar.get(Calendar.MONTH);
        final int mdayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int mmo = mmonthOfYear + 1;
        String mm;
        if (mmo < 10) {
            StringBuffer str = new StringBuffer("0");
            mm = str.append(mmo).toString();
        } else {
            mm = String.valueOf(mmo);
        }
        String dd = "";
        if (mdayOfMonth < 10) {
            StringBuffer str = new StringBuffer("0");
            dd = str.append(mdayOfMonth).toString();
        } else {
            dd = String.valueOf(mdayOfMonth);
        }
        DatePickerDialog datePicker = new DatePickerDialog(vReport.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String mon = "";
                int month = monthOfYear + 1;
                if (month < 10) {
                    StringBuffer str = new StringBuffer("0");
                    mon = str.append(month).toString();
                } else {
                    mon = String.valueOf(month);
                }
                String day = "";
                if (dayOfMonth < 10) {
                    StringBuffer str = new StringBuffer("0");
                    day = str.append(dayOfMonth).toString();
                } else {
                    day = String.valueOf(dayOfMonth);
                }
                String date = year + "-" + mon + "-" + day;
                tv.setText(date);
                StringBuffer temp = new StringBuffer(date);
                datePicked = temp.append("T00:00:00+10:00").toString();

            }
        }, myear, mmonthOfYear, mdayOfMonth);
        DatePicker dp = datePicker.getDatePicker();
        dp.setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }


}



