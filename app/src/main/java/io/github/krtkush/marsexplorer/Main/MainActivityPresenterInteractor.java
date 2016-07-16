package io.github.krtkush.marsexplorer.Main;

/**
 * Created by kartikeykushwaha on 16/07/16.
 */
public interface MainActivityPresenterInteractor {

    /**
     * Method to get the max possible SOL for a specified rover.
     * The API is hit for SOL 1, from which the max SOL is extracted.
     * @param roverName Name of the rover for which max SOL is required.
     */
    void getMaxSol(String roverName);

    /**
     * Method ot get the latest weather conditions on Mars
     */
    void getMarsWeather();

    void unsubscribeMaxSolRequest();
    void unsubscribeMarsWeatherRequest();
}
