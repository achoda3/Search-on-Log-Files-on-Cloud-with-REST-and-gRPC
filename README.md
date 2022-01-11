# Search-on-Log-Files-on-Cloud-with-REST-and-gRPC
Description:
Essentially, The LogFileUpload can be used to generate random log files of data which are then immediately pushed to an amazon s3 bucket. If done in an EC2 instance, with proper IAC permissions, no extra authentication would be required (the access keys will be taken from the ec2 environemnt variables). Then the REST or the gRPC can be run with a time argument and delta time (so if time is 00:12:00, and delta time is 00:01:00, it will search in interval of 00:11:00 to 00:13:00) for any logs in that time interval. <br>
Youtube Videos and how to run: <br><br>
LogFileUpload <br>
To get Log Files into the EC2 instance, scp the assembled jar file from GenerateLogFiles using sbt shell
Then ssh into the instance and run it with java -jar "JAR_NAME"
PART1 : https://youtu.be/N36divmE6IM <br><br>
gRPC <br> 
To use gRPC, start up the server class of the gRpc and then run the client with the time intervals and delta times you want as the arguments
and the server will send the request to the lambda and send back the final info to the client.
PART2 : https://youtu.be/s71vMuwYx9I <br><br>
REST <br>
To use REST services to find out whether the intervals are present or not, run the ClientHTTP with input values of the time and delta time
Then lambda function will be called and calculate if it is present or not and retun value to the Client
PART3 : https://youtu.be/FxzSYQSvwmE 


