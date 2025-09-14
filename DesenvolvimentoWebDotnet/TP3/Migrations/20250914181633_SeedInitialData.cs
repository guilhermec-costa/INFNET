using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace TP3.Migrations
{
    /// <inheritdoc />
    public partial class SeedInitialData : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "PricePerNight",
                table: "Properties",
                newName: "Price");

            migrationBuilder.RenameColumn(
                name: "Name",
                table: "Properties",
                newName: "PropertyName");

            migrationBuilder.RenameColumn(
                name: "CountryName",
                table: "Countries",
                newName: "Country");

            migrationBuilder.RenameColumn(
                name: "Name",
                table: "Cities",
                newName: "CityName");

            migrationBuilder.InsertData(
                table: "Countries",
                columns: new[] { "Id", "CountryCode", "Country" },
                values: new object[,]
                {
                    { 1, "BR", "Brasil" },
                    { 2, "US", "Estados Unidos" },
                    { 3, "JP", "Japão" }
                });

            migrationBuilder.InsertData(
                table: "Cities",
                columns: new[] { "Id", "CountryId", "CityName" },
                values: new object[,]
                {
                    { 1, 1, "Rio de Janeiro" },
                    { 2, 1, "São Paulo" },
                    { 3, 2, "Nova Iorque" },
                    { 4, 3, "Tóquio" }
                });

            migrationBuilder.InsertData(
                table: "Properties",
                columns: new[] { "Id", "CityId", "PropertyName", "Price" },
                values: new object[,]
                {
                    { 1, 1, "Hotel Copacabana", 750.00m },
                    { 2, 1, "Pousada Ipanema", 500.50m },
                    { 3, 2, "Apartamento na Paulista", 350.00m },
                    { 4, 3, "Loft em Manhattan", 1200.00m },
                    { 5, 4, "Hotel com vista para o Monte Fuji", 1500.00m }
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DeleteData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 1);

            migrationBuilder.DeleteData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 2);

            migrationBuilder.DeleteData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 3);

            migrationBuilder.DeleteData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 4);

            migrationBuilder.DeleteData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 5);

            migrationBuilder.DeleteData(
                table: "Cities",
                keyColumn: "Id",
                keyValue: 1);

            migrationBuilder.DeleteData(
                table: "Cities",
                keyColumn: "Id",
                keyValue: 2);

            migrationBuilder.DeleteData(
                table: "Cities",
                keyColumn: "Id",
                keyValue: 3);

            migrationBuilder.DeleteData(
                table: "Cities",
                keyColumn: "Id",
                keyValue: 4);

            migrationBuilder.DeleteData(
                table: "Countries",
                keyColumn: "Id",
                keyValue: 1);

            migrationBuilder.DeleteData(
                table: "Countries",
                keyColumn: "Id",
                keyValue: 2);

            migrationBuilder.DeleteData(
                table: "Countries",
                keyColumn: "Id",
                keyValue: 3);

            migrationBuilder.RenameColumn(
                name: "PropertyName",
                table: "Properties",
                newName: "Name");

            migrationBuilder.RenameColumn(
                name: "Price",
                table: "Properties",
                newName: "PricePerNight");

            migrationBuilder.RenameColumn(
                name: "Country",
                table: "Countries",
                newName: "CountryName");

            migrationBuilder.RenameColumn(
                name: "CityName",
                table: "Cities",
                newName: "Name");
        }
    }
}
