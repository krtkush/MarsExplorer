package io.github.krtkush.marsexplorer.WeatherJsonDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeykushwaha on 08/06/16.
 */
public class MarsWeatherDM {

    public class Example {

        @SerializedName("report")
        @Expose
        private Report report;

        /**
         *
         * @return
         *     The report
         */
        public Report getReport() {
            return report;
        }

        /**
         *
         * @param report
         *     The report
         */
        public void setReport(Report report) {
            this.report = report;
        }

    }

}
