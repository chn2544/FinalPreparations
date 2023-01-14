pipeline 
{
    agent any
    
    stages
    {
        
      stage("Build")   
      {
          steps
          {
              echo("---Builidng the Project---")
          }
      }
      
       stage("UT Test Execution")   
      {
          steps
          {
              echo("---Running Unit Level Test Cases---")
          }
      }
      
       stage("Run SIT")   
      {
          steps
          {
              echo("---Running System Integration Test Cases---")
          }
      }
      
       stage("Deploy to Dev")   
      {
          steps
          {
              echo("---Deploying to Dev Environment---")
          }
      }
      
      stage("Deploy to Stage")   
      {
          steps
          {
              echo("---Deploying to Staging Environment---")
          }
      }
      
      
      stage("Run on Stage")   
      {
          steps
          {
              echo("---Running on Staging Environment---")
          }
      }
      
      stage("Deploy to Prod")   
      {
          steps
          {
              echo("---Deploying to Production Environment---")
          }
      }
      
      
      stage("Run on Prod")   
      {
          steps
          {
              echo("---Running on Production Environment---")
          }
      }
      
    }
    
}