# WeatherApp

A simple proof of concept weather app.

API Used: https://openweathermap.org/api  
One Call API 3.0 - Weather Data  
Geocoding API - Geocoding device location to friendly name and reverse geocoding search input name to coordinates for weather data    

## Overview

![Screenshot 2022-06-09 at 15 30 54](https://user-images.githubusercontent.com/5444730/172859167-98416504-8296-4c20-934f-85cde0b89e94.png)
![Screenshot 2022-06-09 at 15 31 39](https://user-images.githubusercontent.com/5444730/172859344-eaa9337f-581f-480a-9af8-8b4ac336bfaa.png)


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
   - Permissions (location)
   - Flows / Flow operators
   - Hilt dependancy injection 
   - Material 3 theme implementation
   - Google play services (device location)
   - Figma (used to generate Material 3 colors)
   - **RoomDB - Only on feature branch offline_mode currently.  

## Missing functionality:

### Offline mode
The app currently doesn't support offline mode as time constraints didn't allow me to finish the implementation.  

I have started implementation using roomDB to to store weather data and to observe the data from the database (using flow) and update the db from the api call.  

The code can be found the offline_mode branch: https://github.com/nicobos/WeatherApp/tree/features/offline_mode



