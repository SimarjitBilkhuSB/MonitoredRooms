#include <WiFi.h>
#include <FirebaseESP32.h>

//define the Firebase database
#define FIREBASE_HOST "" //the host link (remove the 'http://' at the front and '/' at the end)
#define FIREBASE_AUTH "" //the database secret ->On Firebase site: Project Overview->Project settings->Service accounts->Database secrets
#define WIFI_SSID "" //SSID name that you want to connect to
#define WIFI_PASSWORD "" //SSID's password

//Define FirebaseESP32 data object
FirebaseData firebaseData;
FirebaseJson json;
int motionSensorPort = 27; //port number that the motion sensor is connected to
int motionData = 0;
int led = 5; // The Microcontroller's (ESP32 Thing) LED 
 
void setup(){
 Serial.begin(115200);
 pinMode(led, OUTPUT);  
 pinMode(motionSensorPort, INPUT);

 //Connecting to the wifi SSID entered above by the user
 WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
 Serial.print("Connecting");
 while (WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(300);
  }
  
  //Connecting to the Firebase database now
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);

 //simple confirmation of successful connection
  Serial.println();
  Serial.println("Connected...");
  
}
 
void loop(){
motionData = digitalRead(motionSensorPort);  //reading the digital output of the motion sensor
 if (motionData == HIGH) { //motion has been detected
   Serial.println("Motion Detected");
   digitalWrite(led, HIGH); //LED turns on
   delay(100); 
  json.set("/motionData", motionData);
  Firebase.updateNode(firebaseData,"/Sensor",json);
 } 
 else{
   digitalWrite(led, LOW); // turns the LED back off
   delay(100);//keeps the motionData to '1' on the database for some time, that way the room is known to be occupied -->have it act like a time buffer of sorts maybe...
   motionData == LOW;
   json.set("/motionData", motionData);
   Firebase.updateNode(firebaseData,"/Sensor",json);
 }
}
