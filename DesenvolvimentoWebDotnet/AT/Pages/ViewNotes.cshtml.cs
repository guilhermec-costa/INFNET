using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AgenciaTurismo.Pages;

public class ViewNotesModel : PageModel
{
    private readonly string _filesPath;

    public List<string> FileNames { get; set; } = new List<string>();
    public string FileContent { get; set; }

    public ViewNotesModel(IWebHostEnvironment environment)
    {
        _filesPath = Path.Combine(environment.WebRootPath, "files");
        if (!Directory.Exists(_filesPath))
        {
            Directory.CreateDirectory(_filesPath);
        }
    }

    public void OnGet(string fileName)
    {
        LoadFiles();
        if (!string.IsNullOrEmpty(fileName))
        {
            var filePath = Path.Combine(_filesPath, fileName);
            if (System.IO.File.Exists(filePath))
            {
                FileContent = System.IO.File.ReadAllText(filePath);
            }
        }
    }

    public async Task<IActionResult> OnPostAsync(string noteContent)
    {
        if (!string.IsNullOrWhiteSpace(noteContent))
        {
            var fileName = $"nota_{DateTime.Now:yyyyMMddHHmmss}.txt";
            var filePath = Path.Combine(_filesPath, fileName);
            await System.IO.File.WriteAllTextAsync(filePath, noteContent);
        }
        return RedirectToPage();
    }
    
    private void LoadFiles()
    {
        FileNames = Directory.GetFiles(_filesPath)
                             .Select(Path.GetFileName)
                             .ToList();
    }
}