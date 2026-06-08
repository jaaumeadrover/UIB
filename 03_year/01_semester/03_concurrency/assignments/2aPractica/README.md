# 2aPracticaConcurrent
  
  In this university assignment we're simulating a restaurant which has 3 saloons, each one with 3 tables.
  There are two different type of threads: smokers and non smokers. If anyone enters an empty saloon, it will be assigned for smokers or non smokers,
  depending on which process has entered first.</br>
  Explanation can be found in this link: https://www.youtube.com/watch?v=TD5m446NXqI&ab_channel=JoanBalaguer
  
## Requirements

1. Gnat Ada Compiler: you can install it via this link: https://www.adacore.com/. </br>
If you're running through Unix OS, you can just do this:

````
sudo apt update 
sudo apt install gnat
````
On the other hand, if you are using Windows System I suggest you to use WSL or download Ada through a Docker Image.


## Execution
  To provide an easier execution of the project, it is given a shell script that removes older files and generates new ones, executing main file.
````
sh start.sh
````
