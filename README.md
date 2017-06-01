# BackupTools_05-15-2017

Date Written: 05/15/2017

Industry: Time of Flight Mass Spectrometer Developer & Manufacturer

Department: Hardware & Software Customer Support

GUI: “GUI.png”
From the GUI the user must define each of the following required parameters:
  •	Script Name – The name of the backup script.
  •	Source Directory - Directory which contains the files and folders to be backed up. 
  •	Backup Directory - The directory where the backup files and folders will be sent into.
  •	Mirror – Defines the backup type as a mirror (or not).

Additionally the user can elect to define some optional parameters:
  •	Log Directory – Defines the name and file path of a log file (text file) which contains information as to which files and directories     where were backup when a script is executed.
  •	Robocopy Switches – Enables the user to evoke some advanced backup functionalities.

Each script that is create is added to the scripts drop down list.  Giving the user to backup multiple directories and set their backup directories to unique locations.  A sequence of scripts can be saved as a *.cpy file format and loaded for repeated use.

Application Description:

This application is essentially a GUI to run robocopy scripts.  I wrote this because I grew tired of angry customers that were upset because their hard drive crashed and they never backup up their data.  They often expected me to recover 1TB of data acquired over 5+ years if instrument usage.  I’d have to explain to them that we are not a data recovery company, but rather a developer and manufacturer of mass spectrometers.  My hope is that by offering a free, and easy way to back up their data, more customers will do so.  It always amazed me how very few customers back up their data.

