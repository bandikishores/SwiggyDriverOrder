# SwiggyDriverOrder
A sample code to assign drivers to orders

## Design
* Start the Application by running main program provided in ```Application.java```
* The Program simulates Drivers & Orders at runtime by randomly placing 
* The application starts assigning orders to drivers based on a score/cost factor. The **lower** the cost, the better will be the chance of selecting user-driver.
* The Optimal solution is picked using [Hungraian Algorithm](https://raw.githubusercontent.com/KevinStern/software-and-algorithms/master/src/main/java/blogspot/software_and_algorithms/stern_library/optimization/HungarianAlgorithm.java).
* The code provides a set of Filters & Criteria
  * **Filter** - If User/Admin wishes to remove some set of order-driver combination from getting selected.
    * Default Provided -
      * Dummy - Currently nothing happens in the Filter.
  * **Criteria** - Which attribute should be given highest priority when selecting a group of Order-Driver.<br/>
    These values can be changed in runtime by calling reload configuration.
    * Default Provided -
      * *Driver to Restaurant Distance* - Default value provided is 0.1
      * *Order Delay Time* - Default Value is 1.0 (*Since for score selection lower is better - the higher the order waiting time, the faster this order needs to be assigned. Meaning the lower score should be, so code for now uses a Max. wait time and subtracts the current wait time with max. time to calculate score. That way the one waiting the longest will get the order first*)
      * *Driver Free Time* - Default Value is 1.0 (*Same as above, higher Driver free time more orders should be given*)
* *Code Entensibility* - 
  * **Criteria** - If newer criteria needs to be added, its a simple task of just implmenting ```ICriteria``` Interface and adding ```@CriteriaQualifier``` annotation to that class.<br/>
    Rest will be taken care of automatically. Developer only needs to worry about the logic they're going to write in his/her own Criteria class.
  * **Filter** - Implementing ```IFilter``` class and annotating with ```@FilterQualifier```
  * **Algorithm** - Changing Algorithm would be simple, Just changing the underlying implementation of calling ```HungarianAlgorithm``` would do. As CostMatrix Sent to the Algorithm is as simple as ```double[][]```.
    So its not tied to the Objects.
  * **Execution Flow change** - This can easily be done by changing the code in ```AssignmentService.assignDriversToOrders``` as it just takes a list of orders and automatically assigns the drivers.


## Assumptions
* Order Processor will be called for a set of Orders and Drivers belonging to a particular Area code and not for entire city. (*Caller should handle this*)
* No Test cases have been added as everything here works in Simulation.
* Distance between resturants is calculated by simply subtrating the areacodes, in real time it'll be an external system which will do that. For now, even that is abstracted in ```LocationService``` so changing the API will not effect the code.
* Some Criteria's are Inverse Match. <br/>Ex: Driver Free Time, If its higher we need to deliver the order faster. So score for them is computed by subtracting current value with a max. value.
* Synchronization of Configuration reload while assignment is happening parallely is not handled. It requires a centralized lock using Redis/Zookeeper to make sure all servers reload the configuration at the same time.
* Scenario's of having too many drivers/orders in memory, Too much of values for Criteria Configuration and too many values to Hungarian Algorithm is not handled. This can be easily handled by having a limit in ```AssignmentService```  to process only a certain set of orders/drivers at any point in time.

## Flow
* Create a set of Drivers
* Create a set of Orders
* Process them.
* Now reset the assignment
* Change the Configuration of Criteria value
* re-run from Process step

### End Points
* Get All Drivers
  ```
  curl -X GET http://localhost:8080/drivers
  ```

* Get All Orders
	```
  curl -X GET http://localhost:8080/orders`
  ``

* Create Random 10 Drivers
	```
  curl -X POST http://localhost:8080/drivers/create/5
  ```

* Create Random 4 Orders
	```
  curl -X POST http://localhost:8080/orders/create/2
  ```

* Process Open Orders (*123 is the location, currently not used*)
	```
  curl -X POST http://localhost:8080/process/123
  ```

* To Reset the orders and drivers status
	```
  curl -X POST http://localhost:8080/configuration/reset
  ```

* Change the priority of criteria's in runtime (*there is not limit on the value used to compute score*)
```
	curl -X POST \
  http://localhost:8080/configuration/criteria \
  -H 'content-type: application/json' \
  -d '[
	{
		"criteria":"DRIVER_TO_RESTAURANT_DISTANCE",
		"value":"0.1"
	},
	{
		"criteria":"ORDER_DELAY_TIME",
		"value":"1.0"
	},
	{
		"criteria":"DRIVER_FREE_TIME",
		"value":"1.0"
	}
]'
```

