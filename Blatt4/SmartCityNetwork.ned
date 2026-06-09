//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see http://www.gnu.org/licenses/.
//

import inet.networklayer.configurator.ipv4.Ipv4NetworkConfigurator;
import inet.node.ethernet.EthernetSwitch;
import inet.node.inet.Router;
import inet.node.inet.StandardHost;
import ned.DatarateChannel;


//
// TODO auto-generated type
//
network SmartCityNetwork
{
    @display("bgb=710,714");
    submodules:
        cli1: StandardHost {
            @display("p=196,65");
        }
        cli2: StandardHost {
            @display("p=320,65");
        }
        srv1: StandardHost {
            @display("p=175,613");
        }
        srv2: StandardHost {
            @display("p=320,613");
        }
        sw1: EthernetSwitch {
            @display("p=253,145");
        }
        sw2: EthernetSwitch {
            @display("p=253,536");
        }
        edge1: Router {
            @display("p=253,222");
        }
        edge2: Router {
            @display("p=253,457");
        }
        r1: Router {
            @display("p=196,292");
        }
        r2: Router {
            @display("p=314,292");
        }
        r3: Router {
            @display("p=196,375;b=5,4");
        }
        r4: Router {
            @display("p=314,375");
        }
        configurator: Ipv4NetworkConfigurator {
            @display("p=576,245");
        }
    connections allowunconnected:
    cli1.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> sw1.ethg++;
    cli2.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> sw1.ethg++;
    sw1.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> edge1.ethg++;

    srv1.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> sw2.ethg++;
    srv2.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> sw2.ethg++;
    sw2.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> edge2.ethg++;

    edge1.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> r1.ethg++;
    r1.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> r3.ethg++;
    r3.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> edge2.ethg++;

    edge1.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> r2.ethg++;
    r2.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> r4.ethg++;
    r4.ethg++ <--> DatarateChannel { datarate = 10Mbps; delay = 0.1us; } <--> edge2.ethg++;
}
