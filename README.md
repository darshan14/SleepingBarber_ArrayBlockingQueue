# SleepingBarber_ArrayBlockingQueue
The Sleeping Barber program is implemented using the ArrayBlockingQueue method.

## Problem: 
A small barber shop has two doors, an entrance and an exit. Inside is a set of M barbers who spends all their lives serving customers, one at a time. Each barber has chair in which the customer sits when they are getting their hair cut. When there are no customers in the shop waiting for their hair to be cut, a barber sleeps in his chair. Customer arrive at random intervals, with mean mc and standard deviation sdc. If a customer arrives and finds the a barber asleep, he awakens the barber, sits in the barber’s chair and sleeps while his hair is being cut. The time taken to cut a customer's hair has a mean mh and standard deviation sdh. If a customer arrives and all the barbers are busy cutting hair, the customer goes asleep in one of the N waiting chairs. When the barber finishes cutting a customer’s hair, he awakens the customer and holds the exit door open for him. If there are any waiting customers, he awakens one and waits for the customer to sit in the barber's chair, otherwise he goes to sleep.

## Solution:
The sleeping barber problem consists of ‘m’ number of barbers, ‘n’ number of chairs and continuously coming customers where the values of ‘m’ and ‘n’ are user input. As the problem stated, each barber has his own chair where either he will cut the customer’s hair or go to the sleep. On the other hand, the customer will awake a barber if he’s sleeping and sit on the cutting chair or if all barbers are busy then go to the waiting room till their turn will come. One additional condition come here that if all waiting chairs are occupied, then barber will leave the shop.

The solution is implemented using <b>ExecuterService interface</b> and <b>ArrayBlockingQueue class</b> from BlockingQueue interface. 

## Algorithm:
1.	Take input as number of barbers and waiting chairs by user. <b>ArrayBlockingQueue</b> is created with the capacity of total number of waiting chairs. BarberShop thread is created and started.
2.	<b>ExecuterSevice</b> class created a thread pool of Barber objects. Number of threads in the pool depends on the barber number which is taken as input from user.
3.	All barber threads are created and started. Initially, no customer is present in the queue, hence sleep method is called. This implies that Barbers are ready in the shop and waiting for customers.
4.	New customer arrives in the shop. Customer Thread is created and started.
5.	If all waiting chairs are full, customer is not adding in the queue and he left the shop. Again, if all barbers are not available and the capacity of waiting queue is not full, customer is adding in the waiting queue (ArrayBlockingQueue) otherwise directly sent to the cutting chair.
6.	A normally distributed number is calculated using nextGausian () method and sent the customer thread to the sleep method using a duration of number with standard deviation of 1.2 and mean 500.
7.	The Barber thread gets active. Customer is removed from the waiting queue and the barber starts cutting hair of customer. 
Customer = queue. Poll ();
availableBarber: = availableBarber – 1;
8.	A normally distributed number is calculated using nextGausian () method and sent the barber thread to the sleep method using a duration of number with standard deviation of 1.2 and mean 2500.
9.	Go to step 4.
10.	After completing sleep period of barber thread, the available barber count is incremented and the hair cutting time is calculated.
availableBarber: = availableBarber + 1;
