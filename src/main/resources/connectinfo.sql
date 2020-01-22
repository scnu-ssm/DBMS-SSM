CREATE TABLE `connectinfo`  
(
  `connect_Id` varchar(32) NOT NULL,
  `user_Id` varchar(32) NOT NULL,
  `connect_Name` varchar(25) NOT NULL,
  `host` varchar(30) NOT NULL,
  `port` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `isSave` int NOT NULL,
  PRIMARY KEY (`connect_Id`)
)
