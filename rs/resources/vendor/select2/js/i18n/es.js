/*! Select2 4.0.5 | https://github.com/select2/select2/blob/master/LICENSE.md */

(function () {
  if (jQuery && jQuery.fn && jQuery.fn.select2 && jQuery.fn.select2.amd) {
    var e = jQuery.fn.select2.amd;
  }

  e.define('select2/i18n/es', [], function () {
    return {
      errorLoading: function () {
        return 'No se pudieron cargar los resultados';
      },
      inputTooLong: function (args) {
        var over = args.input.length - args.maximum;
        var message = 'Por favor, elimine ' + over + ' carácter';
        if (over !== 1) {
          message += 'es';
        }
        return message;
      },
      inputTooShort: function (args) {
        var remaining = args.minimum - args.input.length;
        var message = 'Por favor, introduzca ' + remaining + ' carácter';
        if (remaining !== 1) {
          message += 'es';
        }
        return message;
      },
      loadingMore: function () {
        return 'Cargando más resultados…';
      },
      maximumSelected: function (args) {
        var message = 'Sólo puede seleccionar ' + args.maximum + ' elemento';
        if (args.maximum !== 1) {
          message += 's';
        }
        return message;
      },
      noResults: function () {
        return 'No se encontraron resultados';
      },
      searching: function () {
        return 'Buscando…';
      }
    };
  });

  return {
    define: e.define,
    require: e.require
  };
})();

