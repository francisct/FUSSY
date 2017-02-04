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

        BusRepo busRepo = new BusRepo();
        
       

        // GET api/values
        [HttpGet]
        [Route("init")]
        public void init()
        {
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

        // GET api/values/5
        [HttpPost]
        [Route("updateBusPosition")]
        public void updateBusPosition([FromBody] User httpUser)
        {
            Bus currentBus = busRepo.getBus(httpUser.id);
            if (currentBus != null)
            {
                currentBus.AddUser(httpUser);
                currentBus.latitude = currentBus.AverageLat();
                currentBus.longitude = currentBus.AverageLon();
            }
            

        }


        // POST api/values
        [HttpPost]
        public IEnumerable<double> getBusPosition(int id)
        {
            double lat = busRepo.getBus(id).latitude;
            double lon = busRepo.getBus(id).longitude;
            return new double[] {lat,lon};
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
