using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(LDApp.Startup))]
namespace LDApp
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
