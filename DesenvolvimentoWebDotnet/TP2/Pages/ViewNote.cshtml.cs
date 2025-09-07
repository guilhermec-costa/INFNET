using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesTurismo.Pages
{
    public class ViewNoteModel : PageModel
    {
        private readonly IWebHostEnvironment _environment;
        public string FileContent { get; private set; }
        public string NoteFileName { get; private set; }

        public ViewNoteModel(IWebHostEnvironment environment)
        {
            _environment = environment;
        }

        public async Task<IActionResult> OnGetAsync(string fileName)
        {
            if (string.IsNullOrEmpty(fileName)) return NotFound();

            var filesDir = Path.Combine(_environment.WebRootPath, "files");
            var filePath = Path.Combine(filesDir, fileName);

            if (!System.IO.File.Exists(filePath)) return NotFound();

            NoteFileName = fileName;
            FileContent = await System.IO.File.ReadAllTextAsync(filePath);

            return Page();
        }
    }
}