CONTENTS OF THIS FILE
---------------------

* Introduction
* Requirements
* Installation
* Configuration

INTRODUCTION
------------

This application would be able to consume lightning strikes and alert areas within the range.  

REQUIREMENTS
------------

This project has been created using Java with the Spring-Boot framework. Upon processing of lightning strike, application will search for Asset with the same location and print out an alert.

The alert will be follow the format below:
```
lightning alert for <assetOwner>:<assetName>
```

An example of striker coming off of the exchange looks like this:
```
{
    "flashType": 1,
    "strikeTime": 1386285909025,
    "latitude": 33.5524951,
    "longitude": -94.5822016,
    "peakAmps": 15815,
    "reserved": "000",
    "icHeight": 8940,
    "receivedTime": 1386285919187,
    "numberOfSensors": 17,
    "multiplicity": 1
}
```

As the lightning strike has a long/lat format while the Asset has a quadKey format, Bing Util Tools library has been used to convert the long/lat format for comparison. The conversion could also be found here:
http://msdn.microsoft.com/en-us/library/bb259689.aspx

An example of an 'asset' is as follows:
```
{
"assetName":"Dante Street",
"quadKey":"023112133033",
"assetOwner":"6720"
}
```

To avoid recipients from receiving multiple alerts at the same time, all alerts are stored and will prevent further alerts to the same Asset.  

###Where
- flashType=(0='cloud to ground', 1='cloud to cloud', 9='heartbeat')
- strikeTime=the number of milliseconds since January 1, 1970, 00:00:00 GMT
###Note
- A 'heartbeat' flashType is not a lightning strike. It is used internally by the software to maintain connection.

LOCAL SETUP
------------

1. Install Java 8 JRE to the machine/server
2. Clone the repository to local machine using this link: https://github.com/sea-toriado/lightning.git
3. Clean and install maven dependencies using command below:
```aidl
./mvnw clean install
```
4. After installing maven dependencies, use command below to run the application:
```aidl
./mvnw spring-boot:run
```

SERVER SETUP
------------

1. Install Java 8 JRE to the machine/server
2. Build jar file using the command below:
```aidl
./mvnw -P<profile> clean verify
```
3. Upload the jar file to the server
4. Use the command below to start the application:
```aidl
java -jar <jar file location>/lightning.jar & 
```

CONFIGURATION
-------------

###Properties YAML file
- weather.assets.path - file path to the Assets file 
- weather.lightning.path - file path to the Lightning file
- weather.alerted-assets.path - file path to the file containing Information for Alerts
- weather.map.zoom-level - zoom level used for parsing long/lat to quadKey
- weather.alert.message - message format of the alert to be printed 
- weather.alert.exception.flash-type - flash type that would be ignored upon processing
