using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TP3.Migrations
{
    /// <inheritdoc />
    public partial class AddSoftDeleteToProperty : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "DeletedAt",
                table: "Properties",
                type: "TEXT",
                nullable: true);

            migrationBuilder.UpdateData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 1,
                column: "DeletedAt",
                value: null);

            migrationBuilder.UpdateData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 2,
                column: "DeletedAt",
                value: null);

            migrationBuilder.UpdateData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 3,
                column: "DeletedAt",
                value: null);

            migrationBuilder.UpdateData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 4,
                column: "DeletedAt",
                value: null);

            migrationBuilder.UpdateData(
                table: "Properties",
                keyColumn: "Id",
                keyValue: 5,
                column: "DeletedAt",
                value: null);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "DeletedAt",
                table: "Properties");
        }
    }
}
