using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FussyBackEnd
{
    public class BusRepo
    {
        public List<Bus> busList = new List<Bus>();


        public void AddBus(Bus bus)
        {
            if (busList.Exists(x => x.getId() == bus.getId())) { }
            else busList.Add(bus);

        }
        public void RemoveBus(int id)
        {
            foreach (Bus bus in busList)
            {
                if (bus.getId() == id)
                {
                    busList.Remove(bus);
                }
            }
        }
    }
}

