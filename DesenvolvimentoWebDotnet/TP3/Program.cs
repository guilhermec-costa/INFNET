using Microsoft.EntityFrameworkCore;
using TP3.Data;
using TP3.Services;

var builder = WebApplication.CreateBuilder(args);

var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
builder.Services.AddDbContext<CityBreaksContext>(options =>
    options.UseSqlite(connectionString));

// Add services to the container.
builder.Services.AddRazorPages();
builder.Services.AddScoped<ICityService, CityService>();
builder.Services.AddScoped<IPropertyService, PropertyService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();

app.UseRouting();

app.UseAuthorization();

app.MapStaticAssets();
app.MapRazorPages()
   .WithStaticAssets();

app.Run();
