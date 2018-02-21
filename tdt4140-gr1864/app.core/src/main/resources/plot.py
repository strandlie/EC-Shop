import json
import matplotlib.pyplot as plt
import numpy as np
import sys

data = json.loads(open(sys.argv[1]).read())

x, y = [], []

for coordinate in data['path']:
  x.append(coordinate['x'])
  y.append(coordinate['y'])

plt.scatter(x, y)
plt.show()

