#include <WiFi.h>
#include <FirebaseESP32.h>

//define the Firebase database
#define FIREBASE_HOST ""
#define FIREBASE_AUTH ""
#define WIFI_SSID "" //SSID name that you want to connect to
#define WIFI_PASSWORD "" //SSID's password

//Define FirebaseESP32 data object
FirebaseData firebaseData;
FirebaseJson json;
int motionSensorPort = 27; 
int motionData = 0;
int led = 5; // The Microcontroller's (ESP32 Thing) LED 
 
void setup(){
 Serial.begin(115200);
 pinMode(led, OUTPUT);  
 pinMode(motionSensorPort, INPUT);

 //Connecting to the wifi SSID entered above by the user
 WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  
  //Connecting to the Firebase database now
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);

 
  
}
 
void loop(){
String occupancy;
motionData = digitalRead(motionSensorPort);  //reading the digital output of the motion sensor
 if (motionData == HIGH) { //motion has been detected
   Serial.println("Motion Detected");
   occupancy = "Occupied";
   digitalWrite(led, HIGH); //LED turns on
   delay(300); 

   json.set("/roomOccupancy", occupancy);
   Firebase.updateNode(firebaseData,"",json); //node path is "/Users/UID/Rooms/RoomID"
 } 
 else{
   digitalWrite(led, LOW); // turns the LED back off
   delay(300);//keeps the motionData to '1' on the database for some time, that way the room is known to be occupied -->have it act like a time buffer of sorts maybe...
   motionData == LOW;
   occupancy = "Unoccupied";
   json.set("/roomOccupancy", occupancy);
   Firebase.updateNode(firebaseData,"",json); //node path is "/Users/UID/Rooms/RoomID"
 }
}
