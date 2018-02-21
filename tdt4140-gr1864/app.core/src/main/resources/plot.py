"""Plots a data dump from the data mocking program.
Installing dependencies: pip install -r requirements.txt
Usage: python plot.py data.json"""

import json
import matplotlib.pyplot as plt
import sys

data = json.loads(open(sys.argv[1]).read())

x, y = [], []

for coordinate in data['path']:
  x.append(coordinate['x'])
  y.append(coordinate['y'])

plt.scatter(x, y)
plt.show()
