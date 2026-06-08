
# 3a Pràctica Programació Concurrent
This assignment consists in developing a simulation for a sushi restaurant. In it you can found two different threads: the cooker and pepole who come to eat.
Cooker mades a determined number of pieces for each execution, and they are assigned to a RabbitMQ queue. 

In this link, you'll be able to see one execution and its results: <br />
https://www.youtube.com/watch?v=TzAFLIMRGpQ&ab_channel=JoanBalaguer



## 1.Config
  In order to have this software properly executing you should meet this requirements:
  
  1. Golang interpreter and its libraries
  2. RabbitMQ

## 2.Executing

  ### 1. Using docker
   If you haven't got RabbitMQ installed in your computer, you should execute this cmd and it will be installed in a docker container.
    
  `````
  sh make.sh
  ```````
  
  
  ### 2. Without docker
  You should first run rabbitmq and rabbitmq-management-ui(not mandatory,just to manage how it works). Then you should execute these following commands:
  
  ``````
  go mod init {path}
  ``````
  Where path is a chosen path, i.e example.com/greetings if you want your module to be named greetings. Then you do the following imports:
  
  ``````
  go get github.com/streadway/amqp
  ``````
  
  ``````
  go get github.com/rabbitmq/amqp091-go
  ``````
  ## 3.Rabbit-Management-UI
  If desired, you can see whats happening between processes by entering localhost, concretely port 15672.
  Click the following link:
  
  [https://localhost:15672](https://localhost:15672)
  
