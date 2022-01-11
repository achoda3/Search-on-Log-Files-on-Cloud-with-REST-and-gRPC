# HW3PrivateRepo
Youtube Videos:
To get Log Files into the EC2 instance, scp the assembled jar file from GenerateLogFiles using sbt shell
Then ssh into the instance and run it with java -jar "JAR_NAME"
PART1 : https://youtu.be/N36divmE6IM

To use gRPC, start up the server class of the gRpc and then run the client with the time intervals and delta times you want as the arguments
and the server will send the request to the lambda and send back the final info to the client.
PART2 : https://youtu.be/s71vMuwYx9I

To use REST services to find out whether the intervals are present or not, run the ClientHTTP with input values of the time and delta time
Then lambda function will be called and calculate if it is present or not and retun value to the Client
PART3 : https://youtu.be/FxzSYQSvwmE

"# Search-on-Log-Files-on-Cloud-with-REST-and-gRPC" 
