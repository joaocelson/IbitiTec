using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(CampeonatoLD_app.Startup))]
namespace CampeonatoLD_app
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
