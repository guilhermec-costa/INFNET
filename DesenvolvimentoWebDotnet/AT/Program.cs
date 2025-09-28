using AgenciaTurismo.Data;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddAuthentication("MyCookieAuth").AddCookie("MyCookieAuth", options =>
{
    options.Cookie.Name = "MyCookieAuth";
    options.LoginPath = "/Account/Login";
});
builder.Services.AddAuthorization();
builder.Services.AddRazorPages();

builder.Services.AddDbContext<AgenciaTurismoContext>(options =>
    options.UseSqlite(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<AgenciaTurismo.Services.ReservaService>();


var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var reservaService = scope.ServiceProvider.GetRequiredService<AgenciaTurismo.Services.ReservaService>();
    reservaService.CapacityReached += (sender, message) => {
        Console.ForegroundColor = ConsoleColor.Red;
        Console.WriteLine($"[EVENTO DE ALERTA]: {message}");
        Console.ResetColor();
    };
}

if (!app.Environment.IsDevelopment())
{
  app.UseExceptionHandler("/Error");
  app.UseHsts();
}

app.UseHttpsRedirection();

app.UseRouting();
app.UseAuthentication();
app.UseAuthorization();

app.MapStaticAssets();
app.MapRazorPages()
   .WithStaticAssets();

app.Run();
