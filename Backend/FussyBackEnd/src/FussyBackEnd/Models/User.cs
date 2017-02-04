using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FussyBackEnd
{
    public class User
    {
        public int id { get; set; }
        public double longitude { get; set; }
        public double latitude { get; set; }
        public Bus bus { get; set; }

        public User(int id, double longitude, double latitude, Bus bus)
        {
            this.id = id;
            this.longitude = longitude;
            this.latitude = latitude;
            this.bus = bus;
        }
        public User(int id, double longitude, double latitude)
        {
            this.id = id;
            this.longitude = longitude;
            this.latitude = latitude;
            this.bus = null;
        }

        

    }
}
