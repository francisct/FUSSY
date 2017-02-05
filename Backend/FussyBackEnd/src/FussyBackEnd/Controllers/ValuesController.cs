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
                Bus bus1 = new Bus(1);
                Bus bus2 = new Bus(2);
                Bus bus3 = new Bus(3);
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
        public String Get()
        {
            return "Completement correct";
        }

        [HttpGet]
        [Route("updateBusPosition")]
        public String Get([FromQuery] int userId, [FromQuery] int busId, [FromQuery] double lat, [FromQuery] double lon)
        {
            Bus currentBus = busRepo.getBus(busId);
            if (currentBus != null)
            {
        //        Bus bus = new FussyBackEnd.Bus(busId);
                User user = new FussyBackEnd.User(userId, lon, lat, currentBus);
                currentBus.AddUser(user);
                currentBus.latitude = currentBus.AverageLat();
                currentBus.longitude = currentBus.AverageLon();
                busRepo.AddBus(currentBus);
            }

            return "ok!";

        }


        // POST api/values
        [HttpGet]
        [Route("getBusPosition")]
        public IEnumerable<double> Get([FromQuery]int id)
        {
            double lat = busRepo.getBus(id).latitude;
            double lon = busRepo.getBus(id).longitude;
            return new double[] {lat,lon};
        }

      
    }
}
