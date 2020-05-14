# Remote Control Server

This server exposes RESTful APIs that allow to run/terminate predefined local machine processes remotely

## How to use?

1. First configure `cmd2Exec.json`, a configuration file mapping between command name and the executable path you wish to control.
    
    An example for a simple config file: 
   ```json
   {
     "uTorrent": "C:/Progra~1/uTorrent/uTorrent.exe",
     "TeamViewer": "C:/program files/TeamViewer/TeamViewer.exe"
   }
   ```
   
1. Use the REST api to run/terminate the process:
    
    `/v1/runProcess/uTorrent`
    
    `/v1/stopProcess/uTorrent`
