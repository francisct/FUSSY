using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;


namespace FussyBackEnd
{
    public class Bus
    {
        public int id { get; set; }
        public double lon { get; set; }
        public double lat { get; set; }
        public List<User> userList { get; set; }
        public Bus(int id)
        {
            this.id = id;
            userList = new List<User>();
        }

     
        public void AddUser(User newUser)
        {
            for (int i =0; i < userList.Count(); i++)
            {
                if (userList[i].id == newUser.id)
                {
                    userList[i] = newUser;
                    updatePos();
                    return;
                }
                

            }
            userList.Add(newUser);
            updatePos();
            return;
        }

        public void RemoveUser(User oldUser)
        {
            foreach (User user in userList)
            {
                if (oldUser.id == user.id)
                {
                    userList.Remove(oldUser);
                    updatePos();
                    return;
                }              
            }
            return;
        }

        private void updatePos()
        {
            lat = AverageLat();
            lon = AverageLon();
        }

        private double AverageLat()
        {
            double sumLat = 0;
            foreach (User user in userList)
            {
                sumLat = +user.lat;
                
            }
            double avgLat = sumLat / userList.Count();
            return avgLat;
        }

        private double AverageLon()
        {
            double sumLon = 0;
            foreach (User user in userList)
            {
                sumLon = +user.lon;
            }
            double avgLon = sumLon / userList.Count();
            return avgLon;
        }

    }
}
