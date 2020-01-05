import folium, pandas, ast

# get geo data only from rows with non-empty values
locations = pandas.read_csv('i.json', usecols=[3]).dropna()

geos = []

for location in locations.values:
    # add to geos array an evaluated python literal syntax of the data
    geos.append(ast.literal_eval(location[0])['coordinates'])
# initialize and create map
tweet_map = folium.Map(location=[52.8, -2], tiles='Mapbox Bright', zoom_start=7)
# add markers

for geo in geos:
    #tweet_map.circle_marker(location=geo, radius=250)
    folium.CircleMarker(location=geo, radius=5).add_to(tweet_map)

#tweet_map.create_map(path='map.html')
tweet_map.save('map.html')