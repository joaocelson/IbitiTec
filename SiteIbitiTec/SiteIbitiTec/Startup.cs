using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(SiteIbitiTec.Startup))]
namespace SiteIbitiTec
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
