//Use this for testing the Motion Sensor
// -LB

int led = 5; // The Microcontroller's (ESP32 Thing) LED
int motionSensorPort = 27; // The input port on the Microcontroller -->can be any of the other GPIO input ports on the board
int motionDetected = 0;  // The motion sensor detection ( 0 = nothing detected and 1 = motion detected)

void setup() {
 pinMode(led, OUTPUT);     
 pinMode(motionSensorPort, INPUT);     
 Serial.begin(9600);
}

void loop(){
 motionDetected = digitalRead(motionSensorPort); 
 if (motionDetected == HIGH) { //motion has been detected
   Serial.println("Motion Detected");
   digitalWrite(led, HIGH); //LED turns on
   //delay(5000); //time is in ms -->this delay is used to stop it from spamming the message above continously
 } 
 else {
   digitalWrite(led, LOW); // turns the LED back off
 }
} 
