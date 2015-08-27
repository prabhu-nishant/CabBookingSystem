# cabbookingsystem

1. It is a comprehensive system where we need to cater to cab requests from customer in a timely manner. I have made few assumptions to simulate the real application behavior.

2. I am seggregating the activities into two features. "ride now" & "ride later". Any requests which are received within an hour from now will be considered for ride now service. Anything except that will be considered as ride later service.

3. We need a database as well where in we record all request and cab details and also cater to timely updates sent by cab drivers about current location.

4. This application is nothing but a batch job which is designed for ride later service which kicks off every 15 mins to book pending requests.

5. As given in the problem statement there are 100 sectors in a city. I am assuming that there will be atleast 500 cabs owned by the company. Every time this batch job gets executed we pull out pending requests from the database. We also maintain a map of pincodes to list<Cabs> available at that location along with each cab's future schedule.

6. For each request we get a submap with min and max values of pincodes such that we maintain minimum threshold of 15mins to reach destination.

7. We loop through each cab's schedule to check its schedule and see if it can accomodate current booking request keep the profitability constraint.

8. The current or initial location of the cab is updated with the pincode everytime a schedule is completed and this is picked up when we try to build the map at start of each batch sequence.






