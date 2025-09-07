using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesTurismo.Pages
{
    public class SaveNoteModel : PageModel
    {
        private readonly IWebHostEnvironment _environment;

        public SaveNoteModel(IWebHostEnvironment environment)
        {
            _environment = environment;
        }

        [BindProperty]
        public InputModel Input { get; set; }

        public string DownloadLink { get; private set; }

        public void OnGet() { }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            var filesDir = Path.Combine(_environment.WebRootPath, "files");
            Directory.CreateDirectory(filesDir);

            var fileName = $"note-{System.DateTime.UtcNow.Ticks}.txt";
            var filePath = Path.Combine(filesDir, fileName);

            await System.IO.File.WriteAllTextAsync(filePath, Input.Content);

            DownloadLink = $"/files/{fileName}";
            return Page();
        }

        public class InputModel
        {
            [Required]
            public string Content { get; set; }
        }
    }
}