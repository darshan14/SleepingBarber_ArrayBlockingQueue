package com.sleepingBarber;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BarberShop_19211248 extends Thread {
	private static final long OFFICE_CLOSE_TIME = 25000;
	
	private static int totalBarbers, waitingChairs, availableBarbers;

	private static BlockingQueue<Integer> waitingQueue;

	private ExecutorService executor;

	class Customer extends Thread {
		int iD;
		boolean notCut = true;

		BlockingQueue<Integer> queue = null;

		public Customer(int i, BlockingQueue<Integer> queue) {
			iD = i;
			this.queue = queue;
		}

		public void run() {
			while (true) {
				try {					
					if(queue.size() != waitingChairs) {
						/* Inserting customer in the blocking queue */
						queue.add(this.iD);
						if(availableBarbers == 0 && !queue.isEmpty())
							System.out.println("Customer " + this.iD + " is in waiting room.\t Waiting Queue: "+queue);
						else
							this.getHaircut();
					} else {
						System.out.println("The waiting chairs are not empty. Customer "+ this.iD + " has left the barbershop.");
					}
					
				} catch (IllegalStateException exception) {
					System.out.println("Customer Thread Exception..");
					exception.printStackTrace();
				}
				break;
			}
		}

		public void getHaircut() {
			System.out.println("Customer " + this.iD + " took a chair.");
		}
	}

	class Barber implements Runnable {
		BlockingQueue<Integer> queue = null;
		private String name;
		private Instant start, finish;
		
		public Barber(BlockingQueue<Integer> queue, String name) {
			this.name = name;
			this.queue = queue;
			System.out.println("Barber "+name+" is ready.");
		}

		public void run() {
			while (true) { 
				try {
					/* Removing customer from the queue */
					Integer iCustomer = queue.poll(OFFICE_CLOSE_TIME,TimeUnit.MILLISECONDS);
					if(!queue.isEmpty())
						System.out.println("Customer " + iCustomer + " took a chair.");
					if (iCustomer == null) {
						System.out.println("Barber "+name+" is waiting for customer to sit on hair cutting chair.");
						Thread.sleep(500);
						break; 
					} this.cutHair(iCustomer);
				} catch (InterruptedException exception) { 
					System.out.println("Barber Thread exception..");
					exception.printStackTrace();
				}
			}
		}

		public void cutHair(Integer i) {
			Random r2 = new Random();
			long currentTimeHC, timeElapsed;
			/* Decrementing Barber counter as barber started hair cutting */
			availableBarbers--;
			start = Instant.now();
			System.out.println("The barber " + this.name+ " is cutting hair for customer " + i+".");
			try {
				currentTimeHC = (long) (r2.nextGaussian() * 1.2 + 2500);
				/* Thread sleep */
				Thread.sleep(currentTimeHC);
				finish = Instant.now();
				/* Incrementing Barber counter as barber started hair cutting */
				availableBarbers++;
				timeElapsed = Duration.between(start, finish).toMillis();
				System.out.println("The barber " + this.name+ " is finished with hair cutting of customer " +i+ ". Hair Cutting Time: "+timeElapsed);
			} catch (InterruptedException exception) {
				System.out.println("Cut Hair Method exception..");
				exception.printStackTrace();
			}
		} 				  	 
	}

	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		/* User Input for number of Barbers */
		System.out.println("Enter Total Number of Barbers: ");
		totalBarbers = sc.nextInt();
		availableBarbers = totalBarbers;
		/* User Input for total waiting chairs */
		System.out.println("Enter Total Number of Waiting Chairs: ");
		waitingChairs = sc.nextInt();		//waitingChairs = 3;
		System.out.println();
		waitingQueue = new ArrayBlockingQueue<Integer>(waitingChairs);
		BarberShop_19211248 barberShop = new BarberShop_19211248();
		barberShop.start();
	}

	public void run() {
		long currentTimeCustomer, timeElapsed = 0, totalTime = 0;
		Instant start, finish;
		double gauss;
		executor = Executors.newFixedThreadPool(totalBarbers);
		Random r1 = new Random();
			
		/* Creating Barber threads based on user input using ExecuterService framework for multi core multi threading */
		for (int i = 1; i <= totalBarbers; i++) {
			Runnable barber = new Barber(BarberShop_19211248.waitingQueue, Integer.toString(i));
			executor.execute(barber);
		}
		int iCustomer = 1;
		
		/* Running Customer Loop until shop is closed */
		while(totalTime <= OFFICE_CLOSE_TIME) {
			System.out.println("\nCustomer "+iCustomer+" is entered in the shop. Customer Arrival Time: "+totalTime);
			start = Instant.now();
			/* Creating Customer thread */
			Customer customer = new Customer(iCustomer++, BarberShop_19211248.waitingQueue);
			customer.start();
			try {
				gauss = r1.nextGaussian();
				currentTimeCustomer = (long) (gauss * 1.2 + 500);
				/* Thread sleep */
				Thread.sleep(Math.abs(currentTimeCustomer));
				finish = Instant.now();
				timeElapsed = Duration.between(start, finish).toMillis();
				totalTime += timeElapsed;
			} catch (InterruptedException exception) {
				System.out.println("Main Thread Exception..");
				exception.printStackTrace();
			}
		}
	}
	
}