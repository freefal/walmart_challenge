# vim: tabstop=4 shiftwidth=4 noexpandtab

import numpy as np;
import mlpy;
import matplotlib.pyplot as plt;
import matplotlib.dates as md;

def cleanInt (s):
	try:
		i = int(s.strip('"'));
		return i;
	except:
		return -1000;


def cleanFloat (s):
	try:
		i = float(s.strip('"'));
		return i;
	except:
		return -1000;

def cleanStr (s):
	try:
		i = s.strip('"');
		return i;
	except:
		return "";

class DataPoint:
	i = 0;	

station_key_arr = np.loadtxt("key.csv", delimiter=",",skiprows=1,dtype={"names": ("store_nbr", "station_nbr"), "formats": ("i1", "i1")});

weather_arr = np.loadtxt("weather.csv", delimiter=",",skiprows=1,dtype={"names": ("station_nbr","date","tmax","tmin","tavg","depart","dewpoint","wetbulb","heat","cool","sunrise","sunset","codesum","snowfall","preciptotal","stnpressure","sealevel","resultspeed","resultdir","avgspeed"), "formats": ("i1", "S10", "i1", "i1", "i1", "i1", "i1", "i1", "i1", "i1", "i2", "i2", "S20", "f4", "f4", "f4", "f4", "f4", "i2","f4")},converters={2:cleanInt, 3:cleanInt, 4:cleanInt, 5:cleanInt, 6:cleanInt, 7:cleanInt, 8:cleanInt, 9:cleanInt, 10:cleanInt, 11:cleanInt, 12:cleanStr, 13:cleanFloat, 14:cleanFloat, 15:cleanFloat, 16:cleanFloat, 17:cleanFloat, 18:cleanInt, 19:cleanFloat});

train_arr = np.loadtxt("train.csv", delimiter=",",skiprows=1,dtype={"names": ("date","store_nbr","item_nbr","units"), "formats": ("S10","i1", "i1", "i3")});

print (weather_arr[0]["date"]);

train_list = [];

for data_point in train:
	data_point.;
	count += 1;

