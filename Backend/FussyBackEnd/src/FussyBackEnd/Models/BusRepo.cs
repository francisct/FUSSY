using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FussyBackEnd
{
    public class BusRepo
    {
        public List<Bus> busList { get; set; }

        public BusRepo() { busList = new List<Bus>();}

        public void AddBus(Bus newBus)
        {
            foreach (Bus bus in busList)
            {
                if (newBus.id == bus.id)
                {
                    bus.id = newBus.id;
                    bus.lat = newBus.lat;
                    bus.lon = newBus.lon;
                    return;
                }
            }
             busList.Add(newBus);

        }
        public void RemoveBus(int id)
        {
            foreach (Bus bus in busList)
            {
                if (bus.id == id)
                {
                    busList.Remove(bus);
                }
            }
        }

        public Bus getBus(int id)
        {
            foreach (Bus bus in busList)
            {
                if (id == bus.id)
                { return bus; }

            }
            return null;
        }
        
        
        
    }
}

