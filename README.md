# LavilaHotelReservationSystem
UTAR OOAD Assignment

JavaFx installation steps must ready to run these code.
1. install e(fx)clipse 3.6.0 or above
- Launch Eclipse
- Go to HELP > ECLIPSE MARKET PLACE > search for JavaFx > install e(fx)clipse 3.6.0

2. download javafx-sdk-14.0.2.1 or above and place in JAVA file.
- Go to ``https://gluonhq.com/products/javafx/``
- Look for LATEST RELEASE and download ``JavaFX Windows x64 SDK``
- extract the folder
- place the extracted folder into JAVA file 
(C:\Program Files\Java) << applicable to most people

3. Create an user library with JavaFx
- Launch Eclipse 
- Go to WINDOW > PREFERENCE
- search for ``User Libraries``
- click NEW and name it JavaFx (preferred)
- click on the created library [JavaFx] and ``Add External JARs``
- select all javafx. in JAVA file and click open
- click ``Apply and Close``

4. Configure the "Run Configuration" 
- Run the application.Main.java (it will fail for the first time::Error: JavaFX runtime components are missing, and are required to run this application)
- Go to "Run Configuration"
- Select the Main.java that you run and go to "Arguments"
- paste the comment below under the ``VM arguments``
[--module-path "C:\Program Files\Java\javafx-sdk-14.0.2.1\lib" --add-modules javafx.controls,javafx.fxml]

``"C:\Program Files\Java\javafx-sdk-14.0.2.1\lib"``	
	^follow your JAVA file path^
- click Apply and Run