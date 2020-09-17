if __name__ == "__main__":
    f=open("data.js","w");
    f2=open('original.txt', 'r')
    map_data=f2.read()
    map_by_section_data=map_data.split("</coordinates></LinearRing></outerBoundaryIs></Polygon><Polygon><outerBoundaryIs><LinearRing><coordinates>")
    s = "[\n"
    for section in map_by_section_data:
        s+= "\t[\n"
        map_data=section.split()
        a=[]
        for point in map_data:
            a.append(point.split(','))

        for point in a:
            if(len(point) == 2):
                lat=point[1][:min(12,len(point[1]))]
                for i in range(len(lat)-1, -1,-1):
                    if lat[i] == "<":
                        lat = lat[:i]
                s += "\t\t{ lng: " + point[0] + ", lat: " + lat + " },\n"
        s += "\t],\n"
    s+="]"
    f.write(s)
    f.close()

