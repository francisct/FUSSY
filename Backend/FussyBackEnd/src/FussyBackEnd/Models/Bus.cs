using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;


namespace FussyBackEnd
{
    public class Bus
    {
        public int id { get; set; }
        public double longitude { get; set; }
        public double latitude { get; set; }
        public List<User> userList { get; set; }
        public Bus(int id)
        {
            this.id = id;
            userList = new List<User>();
        }

     
     
        public void AddUser(User newUser)
        {
            foreach (User user in userList)
            {
                if (newUser.id == user.id)
                {
                    return;
                }
                

            }
            userList.Add(newUser);
            return;
        }

        public void RemoveUser(User oldUser)
        {
            foreach (User user in userList)
            {
                if (oldUser.id == user.id)
                {
                    userList.Remove(oldUser);
                    return;
                }              
            }
            return;
        }

        public double AverageLat()
        {
            double sumLat = 0;
            foreach (User user in userList)
            {
                sumLat = +user.latitude;
                
            }
            double avgLat = sumLat / userList.Count();
            return avgLat;
        }

        public double AverageLon()
        {
            double sumLon = 0;
            foreach (User user in userList)
            {
                sumLon = +user.longitude;
            }
            double avgLon = sumLon / userList.Count();
            return avgLon;
        }

    }
}
