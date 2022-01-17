# LatLongs-on-WorldMap

The project created by ```Sudhanshu Dekate``` aims to calculate all the intermediate consecutive latitudes and longitudes some distance apart between source and destination.
I have used Google's Directions API to get all routes between source and destination latitudes,longitudes. Based on output obtained, processing is being done using some logic and then all consecutive intermediate cordinates are being displayed.

## Running the Project
As it is a simple interactive command line application, running the project is same as running any java file. Once the application starts, provide data related to source and destination latitudes,longitudes when prompted. After data is given press enter to see the output.
<br> Following is the typical input/output that can be seen in CLI <br>
```
----- Welcome to the Intermediate Co-ordinates Calculator by SUDHANSHU DEKATE -----

Enter SOURCE Latitude and Longitudes separated by space : 
21.126598 79.098464
Enter DESTINATION Latitude and Longitudes separated by space : 
21.125362 79.100416
Enter distance(in metres) the consecutive points should be apart : 
50
Intermediate Cordinates are : 
21.126598,79.098464,
21.126382,79.098887,
21.126231,79.099139,
21.125966,79.099528,
21.1257,79.099917,
21.125432,79.100304,
21.125362,79.100416,
---------------------
```

## Code Structure
The whole code is divided into three packages - <br>
```models``` - This package contains all entities that are being used for storing and maintaining the data<br>
```service``` - This package contains logic for each type of sub-problems such as calculating distance, cordinates, polyline, etc. <br>
```strategy``` - This package contains one single file wherein strategy for populating the cordinates between the two points is written.<br>
