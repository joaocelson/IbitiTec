namespace LDApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initial : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Comerciantes", "Publicidade", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.Comerciantes", "Publicidade");
        }
    }
}
