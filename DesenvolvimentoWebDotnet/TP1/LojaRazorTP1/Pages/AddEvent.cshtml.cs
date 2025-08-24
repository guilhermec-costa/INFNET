using LojaRazor.Models;
using LojaRazor.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace LojaRazor.Pages
{
    public class AddEventModel : PageModel
    {
        private readonly EventService _eventService;

        [BindProperty]
        public Event NovoEvento { get; set; }

        public bool DadosEnviados { get; set; }

        public AddEventModel(EventService eventService)
        {
            _eventService = eventService;

            _eventService.OnEventCreated += (evt) =>
            {
                Console.WriteLine($"[LOG] Evento criado: {evt.Titulo} em {evt.Local} ({evt.Data:dd/MM/yyyy})");
            };
        }

        public void OnGet()
        {
        }

        public void OnPost()
        {
            if (ModelState.IsValid)
            {
                _eventService.AddEvent(NovoEvento);
                DadosEnviados = true;
            }
        }
    }
}
