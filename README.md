# Transport Company Order Processor

This is my individual work project on OOP in Java, which I am improving into a pet project.

---

## Description

The project is a module of a certain service for the delivery of cargos between cities. Its functionality consists of reading a file with a formed customer order in the correct format, processing it and forming a response file. 
Processing means searching for the most cost-effective selection of vehicles to carry out delivery. 
The input file contains a client order with the following items: client contact details, cargo information, origin, destination. 
The output file contains information for further processing, as well as text feedback for the client: 
if the order can be processed, then the expected price and execution time, vehicles that will be used, otherwise - a corresponding message for the client. 
In theory, the program works with already formed databases of vehicles and available routes.

---

## Technologies used in implementation

- works with the route graph using a modified Dijkstra algorithm;

- collections such as ArrayList, HashMap are used;

- the abstract class `TransportUnit` and its subclasses (`Truck`, `Ferry`, etc.) implement logic to determine if a vehicle can traverse a specific route segment;
  
- comparator for ArrayList is used;

- Serialization via ObjectInputStream/ObjectOutputStream is used;

---

## How to run

1. Clone the repository

   ```bash
   git clone https://https://github.com/k3vel/transport_company_order_processor.git
   ```

3. Open `Main.java` and uncomment the marked section that generates demo databases of vehicles and routes. (optional for first start)

4. Run main.java
