using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;

namespace FussyBackEnd
{
    [Route("api/[controller]")]
    public class ValuesController : Controller
    {

        static BusRepo busRepo;
        
       public ValuesController()
        {
            if (busRepo == null)
            {
                busRepo = new BusRepo();
                //data mock:
                User user1 = new User(1, 45.503283, -73.617576);
                User user2 = new User(1, 45.503279, -73.617594);
                Bus bus1 = new Bus(1);
                bus1.AddUser(user1);
                bus1.AddUser(user2);
                Bus bus2 = new Bus(2);
                User user3 = new User(1, 45.501259, -73.615970);
                Bus bus3 = new Bus(3);
                bus3.AddUser(user3);
                Bus bus4 = new Bus(4);
                Bus bus5 = new Bus(5);

                busRepo.AddBus(bus1);
                busRepo.AddBus(bus2);
                busRepo.AddBus(bus3);
                busRepo.AddBus(bus4);
                busRepo.AddBus(bus5);
            }
        }

      

        [HttpGet]
        [Route("createUser")]
        public String Get()
        {
            int count = 1;
            foreach (Bus bus in busRepo.busList)
            {
                count =+ bus.userList.Count();
            }
            count++;
            return count.ToString();
        }

        [HttpGet]
        [Route("updateBusPosition")]
        public String Get([FromQuery] int userId, [FromQuery] int busId, [FromQuery] double lat, [FromQuery] double lon)
        {
            Bus currentBus = busRepo.getBus(busId);
            if (currentBus != null)
            {
                User user = new User(userId, lon, lat, currentBus);
                currentBus.AddUser(user);
                busRepo.AddBus(currentBus);
            }

            return "ok!";

        }


        // POST api/values
        [HttpGet]
        [Route("getBusPosition")]
        public IEnumerable<double> Get([FromQuery]int id)
        {
            Bus bus = busRepo.getBus(id);
            double lat = bus.lat;
            double lon = bus.lon;
            return new double[] {lat,lon};
        }

        [HttpGet]
        [Route("setBusPosition")]
        public IEnumerable<double> Get([FromQuery]int id, [FromQuery] double lat, [FromQuery] double lon)
        {
            Bus bus = busRepo.getBus(id);
            if (bus != null)
            {
                bus.lat = lat;
                bus.lon = lon;
            }
            return new double[] { lat, lon };
        }


    }
}
