using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FussyBackEnd
{
    public class User
    {
        public int id { get; set; }
        public double lat { get; set; }
        public double lon { get; set; }
        public Bus bus { get; set; }

        public User(int id, double lat, double lon, Bus bus)
        {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.bus = bus;
        }
        public User(int id, double lat, double lon)
        {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            this.bus = null;
        }

        

    }
}
