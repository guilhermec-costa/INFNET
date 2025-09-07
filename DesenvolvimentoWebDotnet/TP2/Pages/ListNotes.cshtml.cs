using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesTurismo.Pages
{
    public class ListNotesModel : PageModel
    {
        private readonly IWebHostEnvironment _environment;
        public List<string> FileNames { get; private set; }

        public ListNotesModel(IWebHostEnvironment environment)
        {
            _environment = environment;
        }

        public void OnGet()
        {
            var filesDir = Path.Combine(_environment.WebRootPath, "files");
            if (Directory.Exists(filesDir))
            {
                FileNames = Directory.GetFiles(filesDir)
                                     .Select(Path.GetFileName)
                                     .ToList();
            }
            else
            {
                FileNames = new List<string>();
            }
        }
    }
}