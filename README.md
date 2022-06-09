# WeatherApp

A simple proof of concept weather app.

API Used: https://openweathermap.org/api
One Call API 3.0 - Weather Data.
Geocoding API - Geocoding device location to friendly name and reverse geocoding search input name to coordinates for weather data cal.

## Overview

![Screenshot 2022-06-09 at 14 04 09](https://user-images.githubusercontent.com/5444730/172842624-aa678075-665f-441e-ac17-7bd0090387fc.png)
![Screenshot 2022-06-09 at 14 06 40](https://user-images.githubusercontent.com/5444730/172843043-39109d0b-d914-4bdf-bc60-f9fb4895b6ef.png)


## App technology stack and architecture 
   - Mockito
   - Coroutines
   - Viewbinding
   - Recyclerview
   - Roboelectric
   - Retrofit/okhttp3 
   - MVVM Architecture
   - AndroidX libraries
   - Repository pattern 
   - Coil (Image loading)
   - Flows / Flow operators
   - Hilt dependancy injection 
   - Material 3 theme implementation
   - Google play services (device location)
   - Figma (used to generate Material 3 colors)

## Missing functionality:

### Offline mode
The app currently doesn't support offline mode as time constraints didn't allow me to finish the implementation.  

I have started implementation using roomDB to to store weather data and to observe the data from the database (using flow) and update the db from the api call.  

The code can be found the offline_mode branch: https://github.com/nicobos/WeatherApp/tree/features/offline_mode



